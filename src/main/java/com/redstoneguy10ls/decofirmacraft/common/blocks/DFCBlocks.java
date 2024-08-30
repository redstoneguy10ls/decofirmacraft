package com.redstoneguy10ls.decofirmacraft.common.blocks;

import com.redstoneguy10ls.decofirmacraft.common.blocks.metal.GateBlock;
import com.redstoneguy10ls.decofirmacraft.common.blocks.rock.ColumnBlock;
import com.redstoneguy10ls.decofirmacraft.common.blocks.rock.CustomDFCRockBlocks;
import com.redstoneguy10ls.decofirmacraft.common.blocks.rock.CustomRockBlocks;
import com.redstoneguy10ls.decofirmacraft.common.blocks.rock.DFCRock;
import com.redstoneguy10ls.decofirmacraft.common.items.DFCItems;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.*;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.rock.RockAnvilBlock;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.redstoneguy10ls.decofirmacraft.DecoFirmaCraft.MOD_ID;

public class DFCBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);


    public static final Map<DFCRock, Map<Rock.BlockType, RegistryObject<Block>>> CUSTOM_ROCK_TYPES = Helpers.mapOfKeys(DFCRock.class, rock ->
            Helpers.mapOfKeys(Rock.BlockType.class, type ->
                    register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock))
            )
    );

    public static final Map<DFCRock, Map<Rock.BlockType, DecorationBlockRegistryObject>> CUSTOM_DFC_ROCK_DECORATIONS = Helpers.mapOfKeys(DFCRock.class, rock ->
            Helpers.mapOfKeys(Rock.BlockType.class, Rock.BlockType::hasVariants, type -> new DecorationBlockRegistryObject(
                    register(("rock/" + type.name() + "/" + rock.name()) + "_slab", () -> type.createSlab(rock)),
                    register(("rock/" + type.name() + "/" + rock.name()) + "_stairs", () -> type.createStairs(rock)),
                    register(("rock/" + type.name() + "/" + rock.name()) + "_wall", () -> type.createWall(rock))
            ))
    );

    public static final Map<DFCRock, RegistryObject<Block>> DFC_ROCK_ANVILS = Helpers.mapOfKeys(DFCRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
            register("rock/anvil/" + rock.name(), () -> new RockAnvilBlock(ExtendedProperties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(2, 10).requiresCorrectToolForDrops().blockEntity(TFCBlockEntities.ANVIL), DFCBlocks.CUSTOM_ROCK_TYPES.get(rock).get(Rock.BlockType.RAW)))
    );

    public static final Map<DFCRock, RegistryObject<Block>> DFC_MAGMA_BLOCKS = Helpers.mapOfKeys(DFCRock.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
            register("rock/magma/" + rock.name(), () -> new TFCMagmaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).requiresCorrectToolForDrops().lightLevel(s -> 6).randomTicks().strength(0.5F).isValidSpawn((state, level, pos, type) -> type.fireImmune()).hasPostProcess(TFCBlocks::always)))
    );

    //pillar,roads,tiles
    public static final Map<Rock, Map<CustomRockBlocks, RegistryObject<Block>>> CUSTOM_ROCK_BLOCKS = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(CustomRockBlocks.class, type ->
                    register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock))
            )
    );

    public static final Map<Rock, Map<CustomRockBlocks, DecorationBlockRegistryObject>> ROCK_DECORATIONS = Helpers.mapOfKeys(Rock.class, rock ->
            Helpers.mapOfKeys(CustomRockBlocks.class, CustomRockBlocks::hasVariants, type -> new DecorationBlockRegistryObject(
                    register(("rock/" + type.name() + "/" + rock.name()) + "_slab", () -> type.createSlab(rock)),
                    register(("rock/" + type.name() + "/" + rock.name()) + "_stairs", () -> type.createStairs(rock)),
                    register(("rock/" + type.name() + "/" + rock.name()) + "_wall", () -> type.createWall(rock))
            ))
    );

    public static final Map<DFCRock, Map<CustomDFCRockBlocks, RegistryObject<Block>>> CUSTOM_DFC_ROCK_BLOCKS = Helpers.mapOfKeys(DFCRock.class, rock ->
            Helpers.mapOfKeys(CustomDFCRockBlocks.class, type ->
                    register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock))
            )
    );

    public static final Map<DFCRock, Map<CustomDFCRockBlocks, DecorationBlockRegistryObject>> DFC_ROCK_DECORATIONS = Helpers.mapOfKeys(DFCRock.class, rock ->
            Helpers.mapOfKeys(CustomDFCRockBlocks.class, CustomDFCRockBlocks::hasVariants, type -> new DecorationBlockRegistryObject(
                    register(("rock/" + type.name() + "/" + rock.name()) + "_slab", () -> type.createSlab(rock)),
                    register(("rock/" + type.name() + "/" + rock.name()) + "_stairs", () -> type.createStairs(rock)),
                    register(("rock/" + type.name() + "/" + rock.name()) + "_wall", () -> type.createWall(rock))
            ))
    );

    public static final Map<DFCRock, Map<Ore, RegistryObject<Block>>> DFC_ROCK_ORES = Helpers.mapOfKeys(DFCRock.class, rock ->
            Helpers.mapOfKeys(Ore.class, ore -> !ore.isGraded(), ore ->
                    register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
            )
    );
    public static final Map<DFCRock, Map<Ore, Map<Ore.Grade, RegistryObject<Block>>>> DFC_ROCK_GRADED_ORES = Helpers.mapOfKeys(DFCRock.class, rock ->
            Helpers.mapOfKeys(Ore.class, Ore::isGraded, ore ->
                    Helpers.mapOfKeys(Ore.Grade.class, grade ->
                            register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
                    )
            )
    );
    public static final Map<DFCRock, Map<OreDeposit, RegistryObject<Block>>> DFC_ORE_DEPOSITS = Helpers.mapOfKeys(DFCRock.class, rock ->
            Helpers.mapOfKeys(OreDeposit.class, ore ->
                    register("deposit/" + ore.name() + "/" + rock.name(), () -> new Block(Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.GRAVEL).strength(rock.category().hardness(2.0f)))) // Same hardness as gravel
            )
    );

    public static final Map<Metal.Default, RegistryObject<Block>> METAL_GATES = Helpers.mapOfKeys(Metal.Default.class, Metal.Default::hasTools, metals ->(
            register(("metal/gate/"+ metals.name()), () ->
                    new GateBlock(BlockBehaviour.Properties.of()
                            .mapColor(metals.mapColor())
                            .requiresCorrectToolForDrops()
                            .strength(6.0F, 7.0F)
                            .sound(SoundType.METAL)
                            .noOcclusion()))));

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(DFCBlocks.BLOCKS, DFCItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
