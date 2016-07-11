package forer.tann.videogame.screens.titleScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import forer.tann.videogame.utilities.graphics.Colours;
import forer.tann.videogame.utilities.graphics.Draw;
import forer.tann.videogame.utilities.graphics.TextRenderer;
import forer.tann.videogame.utilities.graphics.font.TannFont;

public class NewsPaper extends Group{
	static final int ARTICLE_GAP = 8;
	int leftY;
	int rightY;
	public NewsPaper(int width, int height) {
		leftY = height-ARTICLE_GAP;
		rightY = height-ARTICLE_GAP;
		setSize(width, height);

		addArticle("Evening Standard 1939", PagePosition.Center, Align.center, 2);
		//		currentY -= ARTICLE_GAP;
		addArticle("\"THIS COUNTRY IS AT WAR\"", PagePosition.Center, Align.center, 4);
		addArticle("Prime Minister's dramatic broadcast this morning", PagePosition.Center, Align.center, 3);

		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think! b;laooi woiefj woiej"
				+ "f owiej foiwjef owijef oiwjefoiwejfowiejfowiejfowiejfwoiejf woiefjwoeifjoi ejfsjgr foieoe rgfioej rgioje roigjeo rigje oirgj eoirgj eoirgj eoirgj eoirjgoeirj goeirjg oirgfojerg "
				+ "ioejrogjiergrejgoiejrgoiejrg", PagePosition.Left, Align.left, 1);
		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Left, Align.left, 1);
		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think! weoirj foweirjfoi erofij eroigf eoirjf uioergioejr goierj gfio ejr", PagePosition.Left, Align.left, 1);
//		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Left, 1);
//		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Left, 1);
		
		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Right, Align.left, 1);
		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Right, Align.left, 1);
		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Right, Align.left, 1);
		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Right, Align.left, 1);
		addArticle("Here is some small text explaining stuff. The nazis are super bad and stuff I think!", PagePosition.Right, Align.left, 1);
	}

	public enum PagePosition{Left, Right, Center}

	private void addArticle(String text, PagePosition position, int textAlign, int fontSize){
		TannFont font=null;
		switch(fontSize){
		case 1: font = TannFont.font; break;
		case 2: font = TannFont.bigFont; break;
		case 3: font = TannFont.biggerFont; break;
		case 4: font = TannFont.biggestFont; break;
		}

		int width=0;
		int x=0;
		int y=0;
		boolean left = position == PagePosition.Center|| position == PagePosition.Left;
		boolean right = position == PagePosition.Center|| position == PagePosition.Right;
		switch(position){
		case Center:
			width = (int) (getWidth()-ARTICLE_GAP);
			x = ARTICLE_GAP;
			y = leftY;
			break;
		case Left:
			width = (int) (getWidth()/2-ARTICLE_GAP);
			x = ARTICLE_GAP;
			y = leftY;
			break;
		case Right:
			width = (int) (getWidth()/2-ARTICLE_GAP);
			x = (int)(getWidth()/2+ARTICLE_GAP);
			y = rightY;
			break;
		default:
			break;
		}
		System.out.println(x+":"+y);
		TextRenderer paperName = new TextRenderer(text, font, width, textAlign, Colours.DARK);
		if(left){
			leftY -= paperName.getHeight();
			leftY-=ARTICLE_GAP;
		}
		if(right){
			rightY -= paperName.getHeight();
			rightY-=ARTICLE_GAP;
		}
		paperName.setPosition(x, y-paperName.getHeight());
		addActor(paperName);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.LIGHT);
		Draw.fillActor(batch, this);
		super.draw(batch, parentAlpha);
	}

}
