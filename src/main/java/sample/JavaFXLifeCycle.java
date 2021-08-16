package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.*;

public class JavaFXLifeCycle
{
	private final ExecutorService executor;
	private Stage primaryStage;

	public JavaFXLifeCycle()
	{
		this.executor = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors() );
	}

	public Stage startJavaFX()
	{
		if( primaryStage == null )
			try
			{
				executor.submit( () -> Application.launch( DummyApplication.class ) );

				primaryStage = DummyApplication.futurePrimaryStage.get( 1, TimeUnit.SECONDS );
			}
			catch( InterruptedException | ExecutionException | TimeoutException cause )
			{
				throw new RuntimeException( "JavaFX failed to start.", cause );
			}

		return primaryStage;
	}

	public void stopJavaFX()
	{
		primaryStage.hide();
		primaryStage = null;
		Platform.exit();
	}

	public static class DummyApplication
			extends Application
	{
		static CompletableFuture< Stage > futurePrimaryStage = new CompletableFuture<>();

		public DummyApplication(){ }

		@Override
		public void start( final Stage primaryStage ) throws Exception
		{
			futurePrimaryStage.complete( primaryStage );
		}

		@Override
		public void stop()
		{
			futurePrimaryStage = null;
		}
	}
}
