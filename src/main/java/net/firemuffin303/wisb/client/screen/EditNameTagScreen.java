package net.firemuffin303.wisb.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.firemuffin303.wisb.Wisb;
import net.firemuffin303.wisb.common.WisbWorldComponent;
import net.firemuffin303.wisb.common.packet.RenameNameTagPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.EditBox;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EditNameTagScreen extends Screen {
    private static final Identifier RENAMEABLE_NAMETAG = new Identifier(Wisb.MOD_ID,"textures/gui/nametaggui_2x.png");

    private TextFieldWidget name;
    private final ItemStack itemStack;
    private final Hand interactionHand;
    private String previousName;
    int xpCost;
    ButtonWidget doneButton;
    ClientPlayerEntity clientPlayerEntity;

    public EditNameTagScreen(ItemStack itemStack, Hand hand, ClientWorld clientWorld) {
        super(Text.translatable("wisb.renameNameTag.title"));
        this.itemStack = itemStack;
        this.interactionHand = hand;
        this.xpCost = ((WisbWorldComponent.WisbWorldComponentAccessor)clientWorld).wisb$getWisbWorldComponent().renameCost;
        this.clientPlayerEntity = MinecraftClient.getInstance().player;
    }

    @Override
    protected void init() {
        this.doneButton = ButtonWidget.builder(ScreenTexts.DONE,(arg) ->{
            this.onDone();
        }).dimensions(this.width / 2 - 100,172, 200,20).build();

        this.addDrawableChild(doneButton);

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL,(arg) ->{
            this.client.setScreen(null);
        }).dimensions(this.width / 2 - 100,196, 200,20).build());

        this.name = new TextFieldWidget(this.textRenderer, this.width / 2 - 60, 94, 120, 12, this.title);
        this.name.setEditableColor(-1);
        this.name.setDrawsBackground(false);
        this.name.setEditable(true);
        this.name.setChangedListener(this::onTextChanged);
        this.name.setMaxLength(50);
        this.name.setText("");
        this.addSelectableChild(this.name);


        if(itemStack != null && itemStack.hasCustomName()){
            this.name.setText(itemStack.getName().getString());
            previousName = this.name.getText();
        }

    }

    public void render(DrawContext drawContext, int i, int j, float f) {
        this.renderBackground(drawContext);
        super.render(drawContext, i, j, f);
        this.renderString(drawContext,i,j);
        this.name.render(drawContext,i,j,f);
    }

    @Override
    public void renderBackground(DrawContext drawContext) {
        super.renderBackground(drawContext);
        int k = (this.width - 200) / 2;
        drawContext.drawTexture(RENAMEABLE_NAMETAG,k,70,0,0,196,74,196,74);


    }

    private void renderString(DrawContext drawContext,int i,int j){
        drawContext.drawCenteredTextWithShadow(this.textRenderer,this.title,this.width/2,40,-1);
        Text text = Text.translatable("container.repair.cost",this.xpCost);
        int color = 8453920;
        if(this.clientPlayerEntity.experienceLevel < this.xpCost && !this.clientPlayerEntity.getAbilities().creativeMode){
            color = 16736352;
        }


        if(!this.itemStack.hasCustomName() && !this.name.getText().isBlank()){
            drawContext.drawText(this.textRenderer,text,this.width/2-60,130,color,true);

        } else if (itemStack.hasCustomName() && !this.name.getText().equals(previousName)) {
            drawContext.drawText(this.textRenderer,text,this.width/2-60,130,color,true);

        }

    }

    private void onTextChanged(String string){
        if((this.clientPlayerEntity.experienceLevel < this.xpCost && !this.clientPlayerEntity.getAbilities().creativeMode)){
            this.doneButton.active = false;
            return;
        }
        this.doneButton.active = true;
    }

    private void onDone() {
        if(MinecraftClient.getInstance().player != null){
            RenameNameTagPacket renameNameTagPacket = new RenameNameTagPacket(this.name.getText(),this.name.getText().equals(this.previousName) ? 0 : this.xpCost,this.interactionHand);
            ClientPlayNetworking.send(renameNameTagPacket);
            this.client.setScreen(null);
        }

    }
}
