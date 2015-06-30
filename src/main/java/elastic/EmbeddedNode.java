package elastic;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class EmbeddedNode {
    public static void main(String[] args) {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("script.disable_dynamic", false).build();
        Node node = NodeBuilder.nodeBuilder().settings(settings).node();

        node.start();
    }

}
