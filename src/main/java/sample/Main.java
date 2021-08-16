package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Main
{
	private final JavaFXLifeCycle lifeCycle;

	public static void main(String[] args) {
		new Main().launch();
	}

	public Main()
	{
		lifeCycle = new JavaFXLifeCycle();
	}

	private void launch()
	{
		final var stage = lifeCycle.startJavaFX();
		Platform.runLater(()->
		{
			try
			{
				final var fxmlLoader = new FXMLLoader( getClass().getResource( "/sample.fxml" ) );
				fxmlLoader.setControllerFactory( c -> new Controller( this ) );
				final Parent parent = fxmlLoader.load();

				stage.setScene( new Scene( parent ) );
				stage.show();
			}
			catch( IOException e )
			{
				e.printStackTrace();
			}
		});
	}

	public void exit()
	{
		Platform.exit();
	}
}
