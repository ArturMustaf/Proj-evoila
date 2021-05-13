package de.test.backend.extensions;

import de.test.backend.entities.repositories.container.ElasticTest;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

@Testcontainers
/**
 * this extension creates and destroys the container after usage again
 */
public class BeforeEveryTest implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;

    @Container
    private static final ElasticsearchContainer elasticsearchContainer = new ElasticTest();


    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            started = true;
            // Your "before all tests" startup logic goes here
            elasticsearchContainer.start();
            // The following line registers a callback hook when the root test context is shut down
            context.getRoot().getStore(GLOBAL).put("any unique name", this);
        }
    }

    @Override
    public void close() {
        // Your "after all tests" logic goes here
        elasticsearchContainer.stop();
    }
}
