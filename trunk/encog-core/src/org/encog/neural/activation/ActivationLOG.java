/*
 * Encog(tm) Core v2.5 
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

package org.encog.neural.activation;

import org.encog.engine.network.flat.ActivationFunctions;
import org.encog.engine.util.BoundMath;
import org.encog.persist.Persistor;

/**
 * An activation function based on the logarithm function.
 * 
 * This type of activation function can be useful to prevent saturation. A
 * hidden node of a neural network is said to be saturated on a given set of
 * inputs when its output is approximately 1 or -1 "most of the time". If this
 * phenomena occurs during training then the learning of the network can be
 * slowed significantly since the error surface is very at in this instance.
 * 
 * @author jheaton
 * 
 */
public class ActivationLOG extends BasicActivationFunction {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 7134233791725797522L;



	/**
	 * @return The object cloned.
	 */
	@Override
	public Object clone() {
		return new ActivationLOG();
	}


	/**
	 * Implements the activation function derivative. The array is modified
	 * according derivative of the activation function being used. See the class
	 * description for more specific information on this type of activation
	 * function. Propagation training requires the derivative. Some activation
	 * functions do not support a derivative and will throw an error.
	 * 
	 * @param d
	 *            The input array to the activation function.
	 */
	public double derivativeFunction(final double x) {
		if (x >= 0) {
			return 1 / (1 + x);
		} else {
			return 1 / (1 - x);
		}
	}

	/**
	 * @return Return true, log has a derivative.
	 */
	public boolean hasDerivative() {
		return true;
	}
		
	/**
	 * @return The Encog Engine ID for this activation type, or -1 if not
	 *         defined by the Encog engine.
	 */
	@Override
	public int getEngineID() {
		// TODO Auto-generated method stub
		return ActivationFunctions.ACTIVATION_LOG;
	}

}
