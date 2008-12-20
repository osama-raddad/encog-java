package org.encog.workbench.frames;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.encog.bot.browse.Browser;
import org.encog.bot.browse.WebPage;
import org.encog.bot.browse.range.DocumentRange;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.manager.EncogCommonFrame;

public class BrowserFrame extends EncogCommonFrame implements TreeSelectionListener {

	private JPanel contentsPanel;
	private JScrollPane dataPanel;
	private JButton goButton;
	private JTextField address;
	private JPanel addressPanel;
	private JPanel bodyPanel;
	private JTree tree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	private Browser browser;	
	private WebPage page;
	private JScrollPane scroll;
	private JTextArea dataText;
	
	public BrowserFrame()
	{
		//
		setTitle("Browse Web Data");
		setSize(640,480);
		Container content = this.getContentPane();
		
		content.setLayout(new GridLayout(2,1));
		
		
		//
		this.contentsPanel = new JPanel();
		content.add(this.contentsPanel);
		
		
		this.contentsPanel.setLayout(new BorderLayout());
		this.addressPanel = new JPanel();
		this.bodyPanel = new JPanel();
		this.addressPanel.setLayout(new BorderLayout());
		this.bodyPanel.setLayout(new BorderLayout());
		this.contentsPanel.add(addressPanel,BorderLayout.NORTH);
		this.contentsPanel.add(bodyPanel,BorderLayout.CENTER);
		this.addressPanel.add(this.address = new JTextField(),BorderLayout.CENTER);
		this.addressPanel.add(this.goButton = new JButton("Go"),BorderLayout.EAST);
		this.address.setText("http://www.heatonresearch.com/");
		this.goButton.addActionListener(this);
		this.root = new DefaultMutableTreeNode("Page");
		this.model = new DefaultTreeModel(this.root);
		this.tree = new JTree(this.model);
		this.scroll = new JScrollPane(this.tree);
		this.bodyPanel.add(this.scroll,BorderLayout.CENTER);
		
		//
		this.dataText = new JTextArea();
		this.dataPanel = new JScrollPane(this.dataText);
		this.dataText.setEditable(false);
		content.add(this.dataPanel);
		
		this.tree.addTreeSelectionListener(this);
		
		
		this.browser = new Browser();	
		this.page = browser.getCurrentPage();
	}
	
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==this.goButton )
		{
			try
			{
				this.browser = new Browser();
				browser.navigate(this.address.getText());
				this.page = browser.getCurrentPage();
					
				root.setUserObject(this.address.getText());
				root.removeAllChildren();

				generateTree(page.getContents(),this.root);
				
				model.reload();
				tree.invalidate();
				
			}
			catch(IOException ex)
			{
				EncogWorkBench.displayError("Error",ex.getMessage());
			}
		}
		
	}
	
	private void generateTree(List<DocumentRange> list,DefaultMutableTreeNode node)
	{
		for(DocumentRange data: list )
		{
			DefaultMutableTreeNode n = new DefaultMutableTreeNode(data); 
			node.add(n);
			generateTree(data.getElements(),n);
			
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getNewLeadSelectionPath();
		if( (path!=null) && (path.getLastPathComponent() instanceof DefaultMutableTreeNode) )
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
			if( node!=null )
			{
				if( node.getUserObject() instanceof DocumentRange )
				{
					DocumentRange range = (DocumentRange)node.getUserObject();
					this.dataText.setText(range.getTextOnly());
				}
			}
		}
		
	}


}
