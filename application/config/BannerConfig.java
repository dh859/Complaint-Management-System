package com.cms.cmsapp.application.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;

public class BannerConfig implements Banner {

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {

        String appName = environment.getProperty("spring.application.name", "CMS");
        String profile = environment.getProperty("spring.profiles.active", "default");
        String javaVersion = System.getProperty("java.version");
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        String bannerMode = environment.getProperty("spring.main.banner-mode", "console");

        out.println("\u001B[36m\u001B[1m");
        out.println("╔═══════════════════════════════════════════════════════════════════════════════════════╗");
        out.printf("║     ██████╗ ███╗   ███╗ ███████╗    \u001B[32m <>  Application \u001B[0m: %-30s \u001B[36m\u001B[1m║%n", appName);
        out.printf("║     ██╔════╝ ████╗ ████║ ██╔════╝   \u001B[33m <>  Profile     \u001B[0m: %-30s \u001B[36m\u001B[1m║%n", profile);
        out.printf("║     ██║      ██╔████╔██║ ███████╗   \u001B[36m <>  Java        \u001B[0m: %-30s \u001B[36m\u001B[1m║%n", javaVersion);
        out.printf("║     ██║      ██║╚██╔╝██║ ╚════██║   \u001B[35m <>  PID         \u001B[0m: %-30s \u001B[36m\u001B[1m║%n", pid);
        out.printf("║     ╚██████╗ ██║ ╚═╝ ██║ ███████║   \u001B[34m <>  Started     \u001B[0m: %-30s \u001B[36m\u001B[1m║%n", bannerMode);
        out.println("║      ╚═════╝ ╚═╝     ╚═╝ ╚══════╝                                                     ║");
        out.println("╚═══════════════════════════════════════════════════════════════════════════════════════╝");
        out.println("\u001B[0m");

        out.println("\u001B[34m\u001B[1m          Complaint Management System \u001B[0m");
        out.println("\u001B[2m──────────────────────────────────────────────────────────────────────────────────────\u001B[0m");
        out.println();
        out.println("\u001B[32m\u001B[1m        ✔ System Ready • CMS Online\u001B[0m");
    }
}
