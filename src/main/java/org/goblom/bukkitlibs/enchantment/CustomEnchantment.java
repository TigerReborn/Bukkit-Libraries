/*
 * The MIT License
 *
 * Copyright 2014 Goblom.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.goblom.bukkitlibs.enchantment;

import java.lang.reflect.Field;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Goblom
 */
public class CustomEnchantment {

    public static class CustomData extends CustomEnchant {

        public CustomData(int id) {
            super(id);
        }

        @Override
        public boolean canEnchantItem(ItemStack itemstack) {
            return false;
        }

        @Override
        public boolean conflictsWith(Enchantment enchantment) {
            return false;
        }

        @Override
        public EnchantmentTarget getItemTarget() {
            return EnchantmentTarget.ALL;
        }

        @Override
        public int getMaxLevel() {
            return 1;
        }

        @Override
        public int getStartLevel() {
            return 1;
        }

        @Override
        public int getWeight() {
            return 1000;
        }

    }

    private abstract class CustomEnchant extends Enchantment {

        public CustomEnchant(int id) {
            super(id);

            if (id > 256) {
                throw new IllegalArgumentException("An enchantment id has to be lower then 256!");
            }

            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, Boolean.valueOf(true));
                Enchantment.registerEnchantment(this);
                f.set(null, f.getBoolean(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public abstract boolean canEnchantItem(ItemStack item);

        public abstract boolean conflictsWith(Enchantment enchantment);

        public abstract EnchantmentTarget getItemTarget();

        public abstract int getMaxLevel();

        public abstract int getStartLevel();

        public abstract int getWeight();

        @Override
        public String getName() {
            return "Usages" + this.getId();
        }
    }
}
