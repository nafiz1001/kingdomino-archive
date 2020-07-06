package kingdomino.helper;

import kingdomino.model.Domino;

import java.util.AbstractSequentialList;
import java.util.ListIterator;

public class DominoPileWrapper extends AbstractSequentialList<Domino> {
    private Domino head;


    public DominoPileWrapper(Domino head) {
        this.head = head;
    }

    private class DominoPileIterator implements ListIterator<Domino> {
        private Domino currDomino;

        public DominoPileIterator(Domino head) {
            this.currDomino = head;
        }

        @Override
        public boolean hasNext() {
            return currDomino.hasNextDomino();
        }

        @Override
        public Domino next() {
            final Domino temp = currDomino;
            currDomino = currDomino.getNextDomino();
            return temp;
        }

        @Override
        public boolean hasPrevious() {
            return currDomino.hasPrevDomino();
        }

        @Override
        public Domino previous() {
            final Domino temp = currDomino;
            currDomino = currDomino.getPrevDomino();
            return temp;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Domino domino) {
            if (currDomino.getPrevDomino() == null)
                Helper.getCurrentGame().setTopDominoInPile(domino);
            else
                currDomino.getPrevDomino().setNextDomino(domino);

            domino.setNextDomino(currDomino.getNextDomino());
        }

        @Override
        public void add(Domino domino) {
            final Domino oldNext = currDomino.getNextDomino();
            currDomino.setNextDomino(domino);
            domino.setNextDomino(oldNext);
        }
    }

    @Override
    public ListIterator<Domino> listIterator(int index) {
        final DominoPileIterator iterator = new DominoPileIterator(head);
        for (int i = 0; i < index; ++i) {
            iterator.next();
        }
        return iterator;
    }

    @Override
    public int size() {
        int size = 0;

        final DominoPileIterator iterator = new DominoPileIterator(head);
        while (iterator.hasNext()) {
            iterator.next();
            ++size;
        }

        return size;
    }
}
