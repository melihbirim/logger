package logger;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.core.Index;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElasticsearchLoggerAppender extends AppenderSkeleton {
    private ExecutorService threadPool = Executors.newSingleThreadExecutor();
    private JestClient client;
    private String applicationName = "application";
    private String type;
    private String hostName = "127.0.0.1";
    private String elasticIndex = "logging-index";
    private String elasticType = "logging";
    private String elasticHost = "http://localhost:9200";

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if (isAsSevereAsThreshold(loggingEvent.getLevel())) {
            threadPool.submit(new AppenderTask(loggingEvent, loggingEvent
                    .getThreadName()));
        }
    }

    @Override
    public void activateOptions() {
        try {
            ClientConfig clientConfig = new ClientConfig.Builder(elasticHost)
                    .multiThreaded(true).build();

            JestClientFactory factory = new JestClientFactory();
            factory.setClientConfig(clientConfig);
            client = factory.getObject();
        } catch (Exception ex) {
        }

        super.activateOptions();
    }

    public String getElasticHost() {
        return elasticHost;
    }

    public void setElasticHost(String elasticHost) {
        this.elasticHost = elasticHost;
    }

    public String getElasticIndex() {
        return elasticIndex;
    }

    public void setElasticIndex(String elasticIndex) {
        this.elasticIndex = elasticIndex;
    }

    public String getElasticType() {
        return elasticType;
    }

    public void setElasticType(String elasticType) {
        this.elasticType = elasticType;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void close() {
        // TODO Auto-generated method stub

    }

    public boolean requiresLayout() {
        // TODO Auto-generated method stub
        return false;
    }

    class AppenderTask implements Callable<LoggingEvent> {

        LoggingEvent loggingEvent;
        String threadName;

        AppenderTask(LoggingEvent loggingEvent, String threadName) {
            this.loggingEvent = loggingEvent;
            this.threadName = threadName;
        }

        protected void writeBasic(Map<String, Object> json, LoggingEvent event) {
            json.put("hostName", getHostName());
            json.put("applicationName", getApplicationName());
            json.put("timestamp", event.getTimeStamp());
            json.put("logger", event.getLoggerName());
            json.put("level", event.getLevel().toString());
            json.put("message", event.getMessage());
            json.put("thread", threadName);
        }

        protected void writeThrowable(Map<String, Object> json,
                                      LoggingEvent event) {
            ThrowableInformation ti = event.getThrowableInformation();
            if (ti != null) {
                Throwable t = ti.getThrowable();
                json.put("className", t.getClass().getCanonicalName());
                json.put("stackTrace", getStackTrace(t));
            }
        }

        protected String getStackTrace(Throwable aThrowable) {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            aThrowable.printStackTrace(printWriter);
            return result.toString();
        }

        /**
         * Method is called by ExecutorService and insert the document into
         * ElasticSearch
         *
         * @return
         * @throws Exception
         */
        @Override
        public LoggingEvent call() throws Exception {
            try {
                if (client != null) {
                    // Set up the es index response
                    String uuid = UUID.randomUUID().toString();
                    Map<String, Object> data = new HashMap<String, Object>();

                    writeBasic(data, loggingEvent);
                    writeThrowable(data, loggingEvent);
                    // insert the document into elasticsearch
                    Index index = new Index.Builder(data)
                            .index(getElasticIndex()).type(getElasticType())
                            .id(uuid).build();
                    client.execute(index);
                }
            } catch (Exception ex) {
            }
            return loggingEvent;
        }
    }

}
