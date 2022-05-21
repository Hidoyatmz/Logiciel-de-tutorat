package app;

import java.time.LocalDate;
import java.util.ArrayList;

import app.config.ConfigManager;
import app.exceptions.PropertyNotExists;
import app.users.StudentBUT1;
import app.users.User;
import app.utils.Utils;

public class UseApp {
	public static void main(String[] args) {
		App app = App.getApp();
		System.out.println(app.getUsers());
	}
}
