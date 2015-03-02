import java.util.AbstractList;

public class ArrayList61B<E> extends AbstractList<E> {

	private int size;
	private E[] array;
	private int numberOfElements;

	public ArrayList61B(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		} else {
		array = (E[]) new Object[initialCapacity];
		size = initialCapacity;
		numberOfElements = 0;
		}
	}

	public ArrayList61B() {
		this(1);
	}

	public E get(int i) {
		if (i < 0 || i >= numberOfElements) {
			throw new IllegalArgumentException();
		} else {
			return array[i];
		}
	}

	@Override
	public boolean add(E item) {
		if (numberOfElements == size) {
			E[] placeHolderArray = array;
			array = (E[]) new Object[size*2];
			for (int i = 0; i < size; i++) {
				array[i] = placeHolderArray[i];
			}
			size = size*2;			
		}
		array[numberOfElements] = item;
		numberOfElements += 1;
		return true;
	}

	public int size() {
		return numberOfElements;
	}
}
