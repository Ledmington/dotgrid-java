/*
 * Dotgrid - Minimalist vector image editor
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

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.Serial;
import java.util.List;

import javax.swing.*;

public final class Dotgrid extends JFrame {
	@Serial
	private static final long serialVersionUID = 437989859749674148L;

	public Dotgrid() {
		super();

		this.setLayout(new BorderLayout());

		final JMenuBar menu = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		for (final String s : List.of("New", "Save", "Open", "Export Vector", "Export Image")) {
			final JMenuItem item = new JMenuItem(s);
			item.addActionListener(e -> System.out.println("clicked " + s));
			fileMenu.add(item);
		}
		final JMenuItem exitButton = new JMenuItem("Exit");
		exitButton.addActionListener(e -> System.exit(0));
		fileMenu.add(exitButton);
		menu.add(fileMenu);

		final JMenu editMenu = new JMenu("Edit");
		for (final String s : List.of("Undo", "Redo")) {
			final JMenuItem item = new JMenuItem(s);
			item.addActionListener(e -> System.out.println("clicked " + s));
			editMenu.add(item);
		}
		menu.add(editMenu);

		final JMenu viewMenu = new JMenu("View");
		for (final String s : List.of("Color Picker", "Toggle Grid", "Toggle Tools")) {
			final JMenuItem item = new JMenuItem(s);
			item.addActionListener(e -> System.out.println("clicked " + s));
			viewMenu.add(item);
		}
		menu.add(viewMenu);

		final JMenu layersMenu = new JMenu("Layers");
		for (final String s : List.of("Foreground", "Middleground", "Background", "Merge Layers")) {
			final JMenuItem item = new JMenuItem(s);
			item.addActionListener(e -> System.out.println("clicked " + s));
			layersMenu.add(item);
		}
		menu.add(layersMenu);

		this.add(menu, BorderLayout.NORTH);

		// this.add(new MainCanvas(), BorderLayout.CENTER);

		// this.add(new ToolBar(), BorderLayout.SOUTH);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException
				| InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException ignored) {
		}

		this.setTitle("Dotgrid");
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocation(
				((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2,
				((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2);
	}
}
