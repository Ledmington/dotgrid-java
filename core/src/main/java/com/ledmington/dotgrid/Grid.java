/*
* Dotgrid - Minimalist vector image editor.
* Copyright (C) 2023-2023 Filippo Barbari <filippo.barbari@gmail.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ledmington.dotgrid;

public final class Grid {

    private final int rows;
    private final int columns;

    public Grid(int r, int c) {
        if (r < 1 || c < 1) {
            throw new IllegalArgumentException(String.format("Cannot create a Grid %,dx%,d", r, c));
        }
        this.rows = r;
        this.columns = c;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * Returns the coordinates of the grid point closest to the given coordinates.
     * The coordinate system is shifted by strideX units to the right and by strideY
     * units up. Every point is distant exactly 2*strideX units horizontally and
     * 2*strideY units vertically. The grid is considered to be in the first quadrant.
     */
    public Pair<Double, Double> closestPoint(double x, double y, double strideX, double strideY) {
        x -= strideX;
        y -= strideY;

        final double lastColumnX = (double) (rows - 1) * 2.0 * strideX;
        final double lastRowY = (double) (columns - 1) * 2.0 * strideY;

        if (x < 0.0) {
            if (y < 0.0) {
                return new Pair<>(strideX, strideY);
            }
            if (y > lastRowY) {
                return new Pair<>(strideX, lastRowY + strideY);
            }
        }
        if (x > lastColumnX) {
            if (y < 0.0) {
                return new Pair<>(lastColumnX + strideX, strideY);
            }
            if (y > lastRowY) {
                return new Pair<>(lastColumnX + strideX, lastRowY + strideY);
            }
        }

        final int columnIdx = (int) ((x + strideX) / (2.0 * strideX));
        final int rowIdx = (int) ((y + strideY) / (2.0 * strideY));

        x = (double) columnIdx * 2.0 * strideX + strideX;
        y = (double) rowIdx * 2.0 * strideY + strideY;

        return new Pair<>(x, y);
    }
}
