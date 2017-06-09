package utils;

/**
 * Clase que maneja una 3-tupla de objetos.
 *
 * @param <F> Tipo de objeto 1.
 * @param <S> Tipo de objeto 2.
 * @param <T> Tipo de objeto 3.
 */
public class Triple<F, S, T> {
    private F first; //first member of pair
    private S second; //second member of pair
    private T third;

    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public void setThird(T third) {
        this.third = third;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public T getThird() {
        return third;
    }
}
