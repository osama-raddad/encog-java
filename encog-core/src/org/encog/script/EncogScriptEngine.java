package org.encog.script;

public interface EncogScriptEngine {
	void run(EncogScript script);
	void setConsole(ConsoleInputOutput console);
}