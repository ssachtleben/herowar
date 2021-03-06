package utils;

import models.entity.game.Material;

import java.util.Comparator;

public class MaterialComparator implements Comparator<Material> {

   @Override
   public int compare(Material mat1, Material mat2) {
      return mat1.getSortIndex().compareTo(mat2.getSortIndex());
   }

}