package src;

import java.util.Objects;

public class Coordinate {
        private int x;
        private int y;
        private int organismSize=1;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
            this.organismSize = 1; // Default organismSize
        }

        public Coordinate(int x, int y, int organismSize) {
            this.x = x;
            this.y = y;
            this.organismSize = organismSize;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSize() {
            return organismSize;
        }

        public void setSize(int organismSize) {
            this.organismSize = organismSize;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    ", organismSize=" + organismSize +
                    '}';
        }
        
//     // Method to calculate Euclidean distance to another coordinate
//        public double distanceTo(Coordinate other) {
//            int dx = this.x - other.x;
//            int dy = this.y - other.y;
//            return Math.sqrt(dx * dx + dy * dy);
//        }
//
//        // Method to calculate Manhattan distance to another coordinate
//        public int manhattanDistanceTo(Coordinate other) {
//            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
//        }
}
