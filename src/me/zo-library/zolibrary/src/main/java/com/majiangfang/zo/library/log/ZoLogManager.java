package com.majiangfang.zo.library.log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZoLogManager {
    private ZoLogConfig config;
    private static ZoLogManager instance;
    private List<ZoLogPrinter> printers = new ArrayList<>();

    private ZoLogManager(ZoLogConfig config, ZoLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static ZoLogManager getInstance() {
        return instance;
    }

    public static void init(@NonNull ZoLogConfig config, ZoLogPrinter... printers) {
        instance = new ZoLogManager(config, printers);
    }

    public ZoLogConfig getConfig() {
        return config;
    }

    public List<ZoLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(ZoLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(ZoLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }
}
