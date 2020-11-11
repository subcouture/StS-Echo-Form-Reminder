package theDefault.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.EchoPower;
import com.megacrit.cardcrawl.relics.FrozenEye;
import javassist.CtBehavior;
import theDefault.util.TextureLoader;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderEnergy"

)

public class EchoReminderPatch {

    private static Texture doubleTexture = TextureLoader.getTexture("resources/echoFormReminderResources/images/Double.png");
    private static TextureAtlas.AtlasRegion doubleRegion = new TextureAtlas.AtlasRegion(doubleTexture, 0, 0, doubleTexture.getWidth(), doubleTexture.getHeight());


    public static void PostFix(AbstractCard __instance, SpriteBatch sb){
        if(AbstractDungeon.player != null && (!AbstractDungeon.player.drawPile.contains(__instance) || AbstractDungeon.player.hasRelic(FrozenEye.ID)) && (AbstractDungeon.player.hasPower(EchoPower.POWER_ID)) && (AbstractDungeon.player.getPower(EchoPower.POWER_ID).amount > 0)){
            sb.begin();
        }
    }

    private static class Locator extends SpireInsertLocator{
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(RelicLibrary.class, "getRelic");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

}
