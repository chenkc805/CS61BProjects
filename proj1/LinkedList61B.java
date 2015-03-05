import java.util.LinkedList;

public class LinkedList61B<E> extends LinkedList<E>{

	private String number;
	private String noun;
	private String definition;
	public LinkedList next;

	public LinkedList61B(String number, String noun, String definition, LinkedList next) {
		this.number = number;
		this.noun = noun;
		this.definition = definition;
		this.next = next;
	}

	public LinkedList61B() {
		this("-1", "Sentinel node noun", "Sentinel node definition", null);
	}

	public String getNumber() {
		return number;
	}

	public String getNoun() {
		return noun;
	}

	public String getDefinition() {
		return definition;
	}
}