package com.base.engine;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

final class PerformanceUtil {

	private static Runtime runtime = Runtime.getRuntime();
	private static OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory
			.getOperatingSystemMXBean();

	private static long maxMem = 0;
	private static long totalFreeMem = 0;
	private static long allocatedMem = 0;
	private static long freeMem = 0;
	private static int processorCores = 0;
	private static double systemCpuLoad = 0;
	private static double processCpuLoad = 0;

	public static void update() {
		/*
		maxMem = runtime.maxMemory() / 1024 / 1024;
		allocatedMem = runtime.totalMemory() / 1024 / 1024;
		freeMem = runtime.freeMemory() / 1024 / 1024;
		totalFreeMem = freeMem + (maxMem - allocatedMem);
		processorCores = os.getAvailableProcessors();
		systemCpuLoad = Util.round(os.getSystemCpuLoad() * 100, 2);
		processCpuLoad = Util.round(os.getProcessCpuLoad() * 100, 2);
		*/
	}

	public static long getMaxMem() {
		return maxMem;
	}

	public static long getAllocatedMem() {
		return allocatedMem;
	}

	public static long getFreeMem() {
		return freeMem;
	}

	public static long getTotalFreeMem() {
		return totalFreeMem;
	}

	public static int getProcessorCores() {
		return processorCores;
	}

	public static double getSystemCpuLoad() {
		return systemCpuLoad;
	}

	public static double getProcessCpuLoad() {
		return processCpuLoad;
	}

}
