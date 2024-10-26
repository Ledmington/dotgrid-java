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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class Main extends Application {
	@Override
	public void start(final Stage stage) {
		final Menu fileMenu = new Menu("File");
		fileMenu.getItems().add(new MenuItem("New"));
		fileMenu.getItems().add(new MenuItem("Open"));
		fileMenu.getItems().add(new MenuItem("Save"));
		fileMenu.getItems().add(new MenuItem("Save As"));
		fileMenu.getItems().add(new SeparatorMenuItem());
		fileMenu.getItems().add(new MenuItem("Export SVG"));
		fileMenu.getItems().add(new MenuItem("Export PNG"));
		fileMenu.getItems().add(new SeparatorMenuItem());
		final MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.addEventHandler(EventType.ROOT, e -> Platform.exit());
		fileMenu.getItems().add(exitMenuItem);

		final MenuBar menubar = new MenuBar();
		menubar.getMenus().add(fileMenu);

		final BorderPane root = new BorderPane();
		root.setTop(menubar);
		root.setCenter(new CanvasPane(stage));

		final Scene scene = new Scene(root, 800, 600);
		stage.setTitle("Dotgrid");
		stage.setScene(scene);
		stage.show();
	}
}
