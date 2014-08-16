package com.ngb.projectzulu.common.blocks.itemblockdeclarations;

import net.minecraft.item.Item;
import com.ngb.projectzulu.common.api.ItemList;
import com.ngb.projectzulu.common.blocks.ItemCoconutSeed;
import com.ngb.projectzulu.common.core.itemblockdeclaration.ItemDeclaration;

import com.google.common.base.Optional;

import cpw.mods.fml.common.registry.GameRegistry;

public class CoconutSeedDeclaration extends ItemDeclaration {

    public CoconutSeedDeclaration() {
        super("CoconutSeed");
    }

    @Override
    protected boolean createItem() {
        ItemList.coconutSeed = Optional.of(new ItemCoconutSeed(6, false, name.toLowerCase()));
        return true;
    }

    @Override
    protected void registerItem() {
        Item item = ItemList.coconutSeed.get();
        GameRegistry.registerItem(item, name);
    }
}
