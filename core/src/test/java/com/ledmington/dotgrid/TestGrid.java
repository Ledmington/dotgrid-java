/*
* Dotgrid - Minimalist vector image editor.
* Copyright (C) 2023-2024 Filippo Barbari <filippo.barbari@gmail.com>
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

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class TestGrid {
    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0})
    void invalidRows(int r) {
        assertThrows(IllegalArgumentException.class, () -> new Grid(r, 2));
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 0})
    void invalidColumns(int c) {
        assertThrows(IllegalArgumentException.class, () -> new Grid(2, c));
    }
}
