package org.encog.app.analyst.script;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.encog.app.analyst.script.generate.AnalystGenerate;
import org.encog.app.analyst.script.ml.AnalystMachineLearning;
import org.encog.app.analyst.script.normalize.AnalystNormalize;
import org.encog.app.analyst.script.prop.ScriptProperties;
import org.encog.app.analyst.script.randomize.AnalystRandomize;
import org.encog.app.analyst.script.segregate.AnalystSegregate;
import org.encog.app.analyst.script.task.AnalystTask;
import org.encog.util.csv.CSVFormat;

public class AnalystScript {

	private DataField[] fields;
	private final AnalystNormalize normalize = new AnalystNormalize();
	private final AnalystRandomize randomize = new AnalystRandomize();
	private final AnalystSegregate segregate = new AnalystSegregate();
	private final AnalystGenerate generate = new AnalystGenerate();
	private final AnalystMachineLearning machineLearning = new AnalystMachineLearning();
	private final Set<String> generated = new HashSet<String>();
	private final Map<String,AnalystTask> tasks = new HashMap<String,AnalystTask>();
	private final ScriptProperties properties = new ScriptProperties();


	public AnalystScript() {
		this.properties.setProperty(ScriptProperties.SETUP_CONFIG_csvFormat, CSVFormat.ENGLISH);
		this.properties.setProperty(ScriptProperties.SETUP_CONFIG_maxClassCount, 50);
		this.properties.setProperty(ScriptProperties.SETUP_CONFIG_allowedClasses, "integer,string");
		this.properties.setProperty(ScriptProperties.SETUP_CONFIG_outputHeaders, true);
	}
	
	/**
	 * @return the fields
	 */
	public DataField[] getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(DataField[] fields) {
		this.fields = fields;
	}


	public void save(OutputStream stream) {
		ScriptSave s = new ScriptSave(this);
		s.save(stream);
	}
	
	public void load(InputStream stream) {
		ScriptLoad s = new ScriptLoad(this);
		s.load(stream);
	}

	/**
	 * @return the normalize
	 */
	public AnalystNormalize getNormalize() {
		return normalize;
	}

	public DataField findDataField(String name) {
		for(DataField dataField: this.fields) {
			if( dataField.getName().equals(name))
				return dataField;
		}
		
		return null;
	}
		
	
	public void markGenerated(String filename) {
		this.generated.add(filename);
	}
	
	public boolean isGenerated(String filename) {
		return this.generated.contains(filename);
	}
	
	/**
	 * @return the randomize
	 */
	public AnalystRandomize getRandomize() {
		return randomize;
	}
	
	/**
	 * @return the generate
	 */
	public AnalystGenerate getGenerate() {
		return generate;
	}

	/**
	 * @return the segregate
	 */
	public AnalystSegregate getSegregate() {
		return segregate;
	}

	public boolean expectInputHeaders(String filename)
	{
		if( isGenerated(filename) )
			return this.properties.getPropertyBoolean(ScriptProperties.SETUP_CONFIG_outputHeaders); 
		else
			return this.properties.getPropertyBoolean(ScriptProperties.SETUP_CONFIG_inputHeaders);
	}

	/**
	 * @return the machineLearning
	 */
	public AnalystMachineLearning getMachineLearning() {
		return machineLearning;
	}
	
	public void clearTasks()
	{
		this.tasks.clear();
	}
	
	public AnalystTask getTask(String name) {
		if( !this.tasks.containsKey(name) ) {
			return null;
		}
		return this.tasks.get(name);
	}
	
	public void addTask(AnalystTask task) {
		this.tasks.put(task.getName(), task);
	}

	public Map<String, AnalystTask> getTasks() {
		return this.tasks;		
	}

	public void init() {
		this.normalize.init(this);		
	}

	/**
	 * @return the properties
	 */
	public ScriptProperties getProperties() {
		return properties;
	}
	
}
