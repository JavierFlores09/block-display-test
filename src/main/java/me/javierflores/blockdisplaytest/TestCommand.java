package me.javierflores.blockdisplaytest;

import java.util.random.RandomGenerator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.arguments.ABlockStateArgument;
import dev.jorel.commandapi.annotations.arguments.AIntegerArgument;
import dev.jorel.commandapi.annotations.arguments.AIntegerRangeArgument;
import dev.jorel.commandapi.wrappers.IntegerRange;

@Command("test")
public class TestCommand {

    @Default
    public static void test(Player sender, @AIntegerRangeArgument IntegerRange scaleRange, @AIntegerArgument int delay) {
        var target = sender.getTargetBlockExact(10);
        test(sender, scaleRange, RandomGenerator.getDefault().nextInt(0, 59),
                target == null ? Material.PRISMARINE.createBlockData() : target.getBlockData(), delay);
    }

    @Default
    public static void test(Player sender, @AIntegerRangeArgument IntegerRange scaleRange, @AIntegerArgument int duration,
            @ABlockStateArgument BlockData blockData, @AIntegerArgument int delay) {
        var location = sender.getLocation();
        if (location == null) {
            return;
        }

        location = location
                .add(location.getDirection()
                        .multiply(2))
                .add(0.5, 0.5, 0.5);

        var display = sender.getWorld().spawn(location, BlockDisplay.class, block -> {
            block.setBlock(blockData);

            var initialScale = scaleRange.getLowerBound();

            if (initialScale > 0) {
                var start = block.getTransformation();
                block.setTransformation(new Transformation(start.getTranslation(), start.getLeftRotation(),
                        start.getScale().mul(initialScale), start.getRightRotation()));
            }

            block.setBillboard(Display.Billboard.FIXED);
        });

        delayed(delay, 
        () -> {
            display.setInterpolationDelay(-1);
            display.setInterpolationDuration(duration);
            var end = display.getTransformation();
        display.setTransformation(new Transformation(end.getTranslation(), end.getLeftRotation(),
                end.getScale().mul(scaleRange.getUpperBound()), end.getRightRotation()));
            });
    }

    private static void delayed(int delay, Runnable execute) {
        if (delay > 0)
            Bukkit.getScheduler().runTaskLater(BlockDisplayTest.inst(), execute, delay);
        else
            execute.run();

    }

}
