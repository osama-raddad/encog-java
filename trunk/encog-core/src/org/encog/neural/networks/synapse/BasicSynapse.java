package org.encog.neural.networks.synapse;

import org.encog.matrix.Matrix;
import org.encog.neural.networks.layers.Layer;

public abstract class BasicSynapse implements Synapse {
	
	private Layer fromLayer;
	private Layer toLayer;
	
	public int getFromNeuronCount() {
		return this.fromLayer.getNeuronCount();		
	}
	
	public int getToNeuronCount() {
		return this.toLayer.getNeuronCount();
	}	


	public Layer getFromLayer() {
		return fromLayer;
	}

	public void setFromLayer(Layer fromLayer) {
		this.fromLayer = fromLayer;
	}

	public Layer getToLayer() {
		return toLayer;
	}

	public void setToLayer(Layer toLayer) {
		this.toLayer = toLayer;
	}
	
	public boolean isSelfConnected()
	{
		return this.fromLayer==this.toLayer;
	}
	
	public void randomize()
	{
	}
	
	abstract public Object clone();

}
