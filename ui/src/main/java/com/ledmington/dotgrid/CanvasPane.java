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

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class CanvasPane extends Pane {

    private static final Color DOT_COLOR = Color.BLACK;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Grid grid;

    public CanvasPane(final Stage parent) {
        super();

        this.grid = new Grid(30, 30);

        this.setWidth(parent.getWidth());
        this.setHeight(parent.getHeight());
        this.canvas = new Canvas(parent.getWidth(), parent.getHeight());
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        parent.widthProperty().addListener((observable, oldValue, newValue) -> update());
        parent.heightProperty().addListener((observable, oldValue, newValue) -> update());
        parent.fullScreenProperty().addListener((observable, oldValue, newValue) -> update());

        this.setOnMouseMoved(e -> {
            final double mouseX = e.getX();
            final double mouseY = e.getY();

            final double width = canvas.getWidth();
            final double height = canvas.getHeight();

            final int r = grid.getRows();
            final int c = grid.getColumns();

            final double paddingX = (width / (double) c) / 2.0;
            final double paddingY = (height / (double) r) / 2.0;

            final Pair<Double, Double> p = grid.closestPoint(mouseX, mouseY, paddingX, paddingY);

            update();

            final double circleRadius = 10.0;
            gc.setFill(DOT_COLOR);
            gc.strokeOval(p.first() - circleRadius / 2.0, p.second() - circleRadius / 2.0, circleRadius, circleRadius);
        });
    }

    private void update() {
        final double width = canvas.getWidth();
        final double height = canvas.getHeight();

        gc.setFill(Color.LIGHTCYAN);
        gc.fillRect(0.0, 0.0, width, height);

        final int r = grid.getRows();
        final int c = grid.getColumns();

        final double paddingX = (width / (double) c) / 2.0;
        final double paddingY = (height / (double) r) / 2.0;
        final double dotRadius = 2.0;

        gc.setFill(DOT_COLOR);
        for (int i = 0; i < c; i++) {
            final double x = paddingX + ((double) i * 2.0 * paddingX);
            for (int j = 0; j < r; j++) {
                final double y = paddingY + ((double) j * 2.0 * paddingY);
                gc.fillOval(x - dotRadius / 2.0, y - dotRadius / 2.0, dotRadius, dotRadius);
            }
        }
    }
}
