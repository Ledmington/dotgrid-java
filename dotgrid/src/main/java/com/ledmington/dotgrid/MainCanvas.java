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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

public final class MainCanvas extends JPanel {
    @Serial
    private static final long serialVersionUID = 437989879749674148L;

    private final Dimension preferredSize = new Dimension(640, 640);
    private final List<Point> gridPoints = new ArrayList<>();
    private final int LINE_KEY = KeyEvent.VK_A;
    private final int padding = 30;
    private final int circleSize = 10;
    private final int innerCircleSize = 4;
    private final int innerCircleSizeBigger = 6; // for "special" grid points
    private final int closestPointSize = 20;
    private Point closestPoint = null;
    private Point mousePosition = null;
    private final List<Point> selectedPoints = new ArrayList<>();
    private final List<Line2D> lines = new ArrayList<>();

    public MainCanvas() {
        this.setMinimumSize(preferredSize);
        this.setPreferredSize(preferredSize);
        this.setSize(preferredSize);
        this.setVisible(true);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.setBackground(Color.WHITE);

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(final MouseEvent e) {}

            @Override
            public void mouseMoved(final MouseEvent e) {
                mousePosition = e.getPoint();
                closestPoint = gridPoints.stream()
                        .min(Comparator.comparing(p -> Math.hypot(p.x - mousePosition.x, p.y - mousePosition.y)))
                        .orElseThrow();
                repaint();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (closestPoint != null) {
                    selectedPoints.add(closestPoint);
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == LINE_KEY) {
                    addLine();
                    selectedPoints.clear();
                    repaint();
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final int width = this.getWidth();
        final int height = this.getHeight();

        g.clearRect(0, 0, width, height);

        // draw grid
        {
            g.setColor(new Color(0, 0, 0, 128));
            gridPoints.clear();
            int row = 0;
            for (int x = padding; x <= width - padding; x += padding) {
                int column = 0;
                for (int y = padding; y <= height - padding; y += padding) {
                    gridPoints.add(new Point(x, y));
                    if (row % 4 == 0 && column % 4 == 0) {
                        g.fillOval(
                                x - innerCircleSizeBigger / 2,
                                y - innerCircleSizeBigger / 2,
                                innerCircleSizeBigger,
                                innerCircleSizeBigger);
                        g.drawArc(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize, 0, 360);
                    } else {
                        g.fillOval(x - innerCircleSize / 2, y - innerCircleSize / 2, innerCircleSize, innerCircleSize);
                    }

                    column++;
                }
                row++;
            }
        }

        if (closestPoint != null) {
            g.drawArc(
                    closestPoint.x - closestPointSize / 2,
                    closestPoint.y - closestPointSize / 2,
                    closestPointSize,
                    closestPointSize,
                    0,
                    360);
        }

        // drawing selected points
        if (!selectedPoints.isEmpty()) {
            for (Point p : selectedPoints) {
                g.setColor(Color.BLACK);
                g.fillOval(p.x - closestPointSize / 2, p.y - closestPointSize / 2, closestPointSize, closestPointSize);
                g.setColor(Color.WHITE);
                g.fillOval(
                        p.x - innerCircleSizeBigger / 2,
                        p.y - innerCircleSizeBigger / 2,
                        innerCircleSizeBigger,
                        innerCircleSizeBigger);
            }
        }

        // drawing lines
        for (Line2D l : lines) {
            g.setColor(Color.BLACK);
            g.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
        }
    }

    private void addLine() {
        for (int i = 0; i < selectedPoints.size() - 1; i++) {
            lines.add(new Line2D.Float(
                    selectedPoints.get(i).x,
                    selectedPoints.get(i).y,
                    selectedPoints.get(i + 1).x,
                    selectedPoints.get(i + 1).y));
        }
    }
}
