package sample;

import javafx.event.ActionEvent;

public class Controller
{
	private final Main main;

	public Controller( final Main main )
	{

		this.main = main;
	}

	public void buttonClicked( final ActionEvent actionEvent )
	{
		main.exit();
	}
}
