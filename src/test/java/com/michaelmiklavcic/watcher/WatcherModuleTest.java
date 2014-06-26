package com.michaelmiklavcic.watcher;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.inject.*;

public class WatcherModuleTest {

    @Test
    public void module_sets_up_instances() {
        Injector injector = Guice.createInjector(new WatcherModule(new String[] {
                "-handler",
                "com.michaelmiklavcic.watcher.WatcherModuleTest$TestHandler" }));
        TestService service = injector.getInstance(TestService.class);
        assertThat(service.runner, instanceOf(WatcherRunner.class));
        assertThat(service.handler, instanceOf(TestHandler.class));
    }

    @Test(expected = CreationException.class)
    public void throws_filewatcher_exception_if_class_does_not_exist() {
        Injector injector = Guice.createInjector(new WatcherModule(new String[] {
                "-handler",
                "com.michaelmiklavcic.watcher.DOESNOTEXIST" }));
        TestService service = injector.getInstance(TestService.class);
    }

    public static class TestService {
        public Handler handler;
        public EventRunner runner;

        @Inject
        public TestService(Handler handler, EventRunner runner) {
            this.handler = handler;
            this.runner = runner;
        }
    }

    public static class TestHandler implements Handler {

        @Override
        public void call(Event event) {
        }

    }
}
