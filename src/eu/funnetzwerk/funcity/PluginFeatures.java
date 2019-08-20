package eu.funnetzwerk.funcity;

public enum PluginFeatures {

	Taxi(true), PLACEHOLDER(false);
	
	private boolean active = false;
	
	PluginFeatures(boolean a) {
		this.active = a;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
}
