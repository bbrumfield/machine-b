
package basics;

import utils.Utils;

public class Square {

    private final int row;
    private final int col;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    private char getFile() {
        return Utils.colToFile(this.getCol());
    }

    private int getRank() {
        return Utils.rowToRank(this.getRow());
    }

    public String toCoordinateSquareString() {
        return this.getRow() + "" + this.getCol();
    }

    public String toChessSquareString() {
        return this.getFile() + "" + this.getRank();
    }

    /* eclipse-generated method */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + this.col;
        result = (prime * result) + this.row;
        return result;
    }

    /* eclipse-generated method */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }
        Square other = (Square) obj;
        if(this.col != other.col) {
            return false;
        }
        if(this.row != other.row) {
            return false;
        }
        return true;
    }

    public Square deepCopy() {
        return new Square(this.row, this.col);
    }

}
