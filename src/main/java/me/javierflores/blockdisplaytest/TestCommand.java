package me.javierflores.blockdisplaytest;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.arguments.ABlockStateArgument;
import dev.jorel.commandapi.annotations.arguments.AIntegerArgument;
import dev.jorel.commandapi.annotations.arguments.AIntegerRangeArgument;
import dev.jorel.commandapi.wrappers.IntegerRange;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;

import java.util.random.RandomGenerator;

@Command("test")
public class TestCommand {

    @Default
    public static void test(CommandSender sender, @AIntegerRangeArgument IntegerRange scaleRange) {
        if (!(sender instanceof Player player)) return;
        var target = player.getTargetBlockExact(10);
        test(player, scaleRange, RandomGenerator.getDefault().nextInt(0, 59),
                target == null ? Material.PRISMARINE.createBlockData() : target.getBlockData());
    }

    @Default
    public static void test(CommandSender sender, @AIntegerRangeArgument IntegerRange scaleRange,
                            @AIntegerArgument(min = 0, max = 59) int interDuration,
                            @ABlockStateArgument BlockData blockData) {
        if (!(sender instanceof Player player)) return;
        var location = player.getLocation()
                .add(player.getFacing().getDirection()
                        .multiply(2))
                .add(0.5, 0.5, 0.5);

        var display = player.getWorld().spawn(location, BlockDisplay.class, block -> {
            block.setBlock(blockData);

            var initialScale = scaleRange.getLowerBound();

            if (initialScale > 0) {
                var start = block.getTransformation();
                block.setTransformation(new Transformation(start.getTranslation(), start.getLeftRotation(),
                        start.getScale().mul(initialScale), start.getRightRotation()));
            }

            block.setBillboard(Display.Billboard.FIXED);
        });
        display.setInterpolationDuration(interDuration);
        var end = display.getTransformation();
        display.setTransformation(new Transformation(end.getTranslation(), end.getLeftRotation(),
                end.getScale().mul(scaleRange.getUpperBound()), end.getRightRotation()));
    }


}
