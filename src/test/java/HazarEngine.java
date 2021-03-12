import eu.builderscoffee.expresso.Main;
import eu.builderscoffee.expresso.utils.BlockData;
import eu.builderscoffee.expresso.utils.Tuple;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.junit.Test;
import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HazarEngine {

    private final HashMap<BlockData.BlockCategory, List<BlockData>> cachedBlock = new HashMap<>();
    public HashMap<BlockData, BlockData> convertBlockdata = new HashMap<>();
    @Getter
    @Setter
    private Main instance;


    public HazarEngine() {
        // On charge l'engine
        load();
    }

    @Test
    public void load() {
        System.out.println("HazardEngine init");
        // Get all bockdata and push then to a list
        Stream.of(BlockData.BlockCategory.values())
                .forEach(blockCategory -> cachedBlock.put(blockCategory, BlockData.blockDataCategory(blockCategory)));
        //cachedBlock.forEach((blockCategory, blockData) -> System.out.print(blockCategory.name() + " " + blockData.size()));
        // Generate random block data
        generateRandomBlockData();
    }

    /***
     * Générer des pairs de blocs pour chaque catégories
     */
    public void generateRandomBlockData() {
        cachedBlock.keySet().forEach(key -> {
            if (key == BlockData.BlockCategory.PLANT) {
                //System.out.println("engine key : " + key);
                List<BlockData> blockData = cachedBlock.get(key);
                //System.out.println("engine key : " + blockData.size());
                Collections.shuffle(blockData); // Mélanger aléatoirement la liste
                int chunkSize = 2; // Taille d'un élement d'une partition
                AtomicInteger counter = new AtomicInteger(); // On empèche la surchauffe des incrémentations :o

                listSplits(blockData);
            }
        });
    }

    public void listSplits(List<BlockData> blockData) {
        // Generic function to split a list into two sublists in Java 8 and above{
            int midIndex = (blockData.size() - 1) / 2;

            List<List<BlockData>> lists = new ArrayList<>(
                    blockData.stream()
                            .collect(Collectors.partitioningBy(s -> blockData.indexOf(s) > midIndex))
                            .values()
            );

            // return an array containing both lists
            List[] twopart =  new List[] {lists.get(0), lists.get(1)};

        List l1 = twopart[0];
        List l2 = twopart[1];

        List<Tuple> set = new ArrayList<>();
        for (Object i : l1)
            for (Object j : l2)
               if(set.stream().noneMatch(tuple -> tuple.x.equals(i)) &&  set.stream().noneMatch(tuple -> tuple.y.equals(j)))
                    set.add(new Tuple(i, j));

        for (Object i : l2)
            for (Object j : l1)
                if(set.stream().noneMatch(tuple -> tuple.x.equals(i)) &&  set.stream().noneMatch(tuple -> tuple.y.equals(j)) && set.stream().noneMatch(tuple -> tuple.x.equals(j) && tuple.y.equals(i)))
                    set.add(new Tuple(i, j));


       set.forEach(pair -> System.out.print("Key " + pair.x + " Valeur " + pair.y + "\n"));
    }
}
