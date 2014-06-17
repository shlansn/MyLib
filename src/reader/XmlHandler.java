package reader;

public class XmlHandler {
	
	private XmlGolemReader xGolem;
	private XmlHeiseReader xHeise;
	private XmlSpiegelReader xSpiegel;
	
	public XmlHandler() {
		this.xGolem = new XmlGolemReader();
		this.xHeise = new XmlHeiseReader();
		this.xSpiegel = new XmlSpiegelReader();
	}
	
	public XmlHandler(XmlGolemReader xmlGolem, XmlHeiseReader xmlHeise, XmlSpiegelReader xmlSpiegel) {
		this.xGolem = xmlGolem;
		this.xHeise = xmlHeise;
		this.xSpiegel = xmlSpiegel;
	}
	
	public XmlGolemReader getxGolem() {
		return xGolem;
	}

	public void setxGolem(XmlGolemReader xGolem) {
		this.xGolem = xGolem;
	}

	public XmlHeiseReader getxHeise() {
		return xHeise;
	}

	public void setxHeise(XmlHeiseReader xHeise) {
		this.xHeise = xHeise;
	}

	public XmlSpiegelReader getxSpiegel() {
		return xSpiegel;
	}

	public void setxSpiegel(XmlSpiegelReader xSpiegel) {
		this.xSpiegel = xSpiegel;
	}

}
