/*
 * Encog(tm) Examples v2.4
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.examples.neural.xorscg;

import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.neural.activation.ActivationSigmoid;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.logic.FeedforwardLogic;
import org.encog.neural.networks.training.propagation.scg.ScaledConjugateGradient;
import org.encog.neural.networks.training.strategy.RequiredImprovementStrategy;
import org.encog.neural.networks.training.strategy.ResetStrategy;
import org.encog.util.logging.Logging;

/**
 * XOR: This example is essentially the "Hello World" of neural network
 * programming.  This example shows how to construct an Encog neural
 * network to predict the output from the XOR operator.  This example
 * uses SCG to train the neural network.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class XorSCG {

	public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
			{ 0.0, 1.0 }, { 1.0, 1.0 } };

	public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };

	public static void main(final String args[]) {
		
		Logging.stopConsoleLogging();
		
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(new ActivationSigmoid(),false,2));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,3));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,3));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,1));
		network.setLogic(new FeedforwardLogic());
		network.getStructure().finalizeStructure();
		network.reset();
		(new RangeRandomizer(-5,5)).randomize(network);

		NeuralDataSet trainingSet = new BasicNeuralDataSet(XOR_INPUT, XOR_IDEAL);
		
		// train the neural network
		final ScaledConjugateGradient train = new ScaledConjugateGradient(network, trainingSet);
		// reset if improve is less than 1% over 5 cycles
		train.addStrategy(new RequiredImprovementStrategy(5));

		int epoch = 1;
		
		do {
			train.iteration();
			System.out
					.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
		} while(train.getError() > 0.01  );

		// test the neural network
		System.out.println("Neural Network Results:");
		for(NeuralDataPair pair: trainingSet ) {
			final NeuralData output = network.compute(pair.getInput());
			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
					+ ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
		}
	}
}