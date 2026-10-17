public class MaxHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.size = 0;
    }

    // Индекс родителя для узла index
    private int parent(int index) {
        return (index - 1) / 2;
    }

    // Индекс левого ребёнка
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    // Индекс правого ребёнка
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    // Обмен двух элементов массива
    private void swap(int i, int j) {
        int sup = heap[i];
        heap[i] = heap[j];
        heap[j] = sup;
    }

    // Вставка нового элемента
    public void insert(int value) {
        if (size == capacity) {
            throw new ArrayIndexOutOfBoundsException("Куча заполнена");
        }
        heap[size] = value;
        size++;
        placeHeapRoot(size - 1);
    }

    // Извлечение максимума
    public int extractMax() {
        if (size == 0) {
            throw new ArrayIndexOutOfBoundsException("Куча пуста");
        }
        int max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        placeHeapBottom(0);          
        return max;
    }

    // Просеивание вверх: элемент поднимается, пока он больше родителя
    private void placeHeapRoot(int i) {
        while (i > 0 && heap[parent(i)] < heap[i]) {
            swap(parent(i), i);
            i = parent(i);
        }
    }

    // Просеивание вниз: элемент опускается, пока он меньше большего из детей
    private void placeHeapBottom(int i) {
        int largest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && heap[left] > heap[largest]) {
            largest = left;
        }

        if (right < size && heap[right] > heap[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(i, largest);
            placeHeapBottom(largest);
        }
    }

    // Публичный метод для вывода всего дерева
    public void printTree() {
        if (size == 0) {
            System.out.println("(куча пуста)");
            return;
        }
        printTree(0, "", "");
    }

    // Рекурсивный вывод дерева
    private void printTree(int index, String prefix, String childPrefix) {
        System.out.println(prefix + heap[index]);

        int left = leftChild(index);
        int right = rightChild(index);

        boolean hasLeft = left < size;
        boolean hasRight = right < size;

        if (hasLeft) {
            printTree(
                    left,
                    childPrefix + (hasRight ? "├── L: " : "└── L: "),
                    childPrefix + (hasRight ? "│   " : "    ")
            );
        }

        if (hasRight) {
            printTree(
                    right,
                    childPrefix + "└── R: ",
                    childPrefix + "    "
            );
        }
    }

    public static void main(String[] args) {
        MaxHeap heap = new MaxHeap(20);

        int[] values = {10, 20, 5, 30, 15, 8, 25, 100, 3, 439, 134, 224, 2043, 1000, 1, 2};

        for (int value : values) {
            heap.insert(value);
        }

        System.out.println("Дерево кучи:");
        heap.printTree();

        System.out.println("\nИзвлекаем максимум: " + heap.extractMax());
        System.out.println("Дерево после извлечения:");
        heap.printTree();
    }
}