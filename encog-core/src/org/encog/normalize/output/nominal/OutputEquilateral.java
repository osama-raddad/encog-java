package org.encog.normalize.output.nominal;

import java.util.ArrayList;
import java.util.List;

import org.encog.normalize.input.InputField;
import org.encog.normalize.output.BasicOutputField;
import org.encog.normalize.output.OutputField;
import org.encog.persist.annotations.EGIgnore;
import org.encog.util.math.Equilateral;

/**
 * 
 * Guiver and Klimasauskas (1991)
 */
public class OutputEquilateral extends BasicOutputField {
	private final List<NominalItem> items = new ArrayList<NominalItem>();
	
	@EGIgnore
	private Equilateral equilateral;
	private int currentValue;
	private double high;
	private double low;

	public OutputEquilateral(double high, double low) {
		this.high = high;
		this.low = low;
	}
	
	public OutputEquilateral()
	{
		
	}

	public void addItem(final InputField inputField, final double low, final double high) {
		final NominalItem item = new NominalItem(inputField, low, high);
		this.items.add(item);
	}
	
	public void addItem(final InputField inputField, final double value) {
		addItem(inputField,value+0.1,value-0.1);
	}
	


	public double calculate(int subfield) {
		return this.equilateral.encode(this.currentValue)[subfield];
	}
	
	
	public int getSubfieldCount()
	{
		return this.items.size()-1;
	}
	
	/**
	 * Determine which item's index is the value.
	 */
	public void rowInit()
	{
		for(int i=0;i<this.items.size();i++)
		{
			NominalItem item = this.items.get(i);
			if( item.isInRange() )
			{
				this.currentValue = i;
				break;
			}
		}
		
		if(this.equilateral==null)
		{
			this.equilateral = new Equilateral(this.items.size(), this.high, this.low);
		}
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public Equilateral getEquilateral() {
		return equilateral;
	}
}
