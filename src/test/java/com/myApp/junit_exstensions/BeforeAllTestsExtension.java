package com.myApp.junit_exstensions;

import com.myApp.Application;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.myApp.containerts.AllContainers.postgreSQL;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

@Log
public class BeforeAllTestsExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;
    final static Lock lock = new ReentrantLock();

    @SneakyThrows
    @Override
    public void beforeAll(ExtensionContext context) {
        try {
            lock.lock();
            if (!started) {
                started = true;
                postgreSQL.start();
                Application.main(new String[]{});
                context.getRoot().getStore(GLOBAL).put("any unique name", this);
            }
        } catch (Exception e) {
            log.info("Контейнеры не развернулись");
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {
        postgreSQL.stop();
    }
}
