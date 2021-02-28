package eu.builderscoffee.expresso.buildbattle.expressos.engine.types;

import eu.builderscoffee.expresso.utils.BlockData;
import lombok.val;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HazarEngine {

    private final HashMap<BlockData.BlockCategory, List<BlockData>> cachedBlock = new HashMap<>();
    private final HashMap<BlockData, BlockData> convertBlockdata = new HashMap<>();

    public HazarEngine() {
        // Get all bockdata and push then to a list
        Stream.of(BlockData.BlockCategory.values())
                .forEach(blockCategory -> cachedBlock.put(blockCategory, BlockData.blockDataCategory(blockCategory)));
    }

    @Test
    public void listToTripleT() {
        cachedBlock.keySet()
                .forEach(key -> {
                    List<BlockData> blockData = cachedBlock.get(key);
                    Collections.shuffle(blockData); // Mélanger aléatoirement la liste
                    int chunkSize = 2; // Taille d'un élement d'une partition
                    AtomicInteger counter = new AtomicInteger(); // On empèche la surchauffe des incrémentations :o

                    // Lister la catégorie sous forme de liste par chunkSize
                    val result = blockData.stream()
                            .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
                            .values();

                    // Pousser la liste sous une format de conversion ( Pour le listener )
                    result.forEach(results -> {
                        // Dans le cas ou l'ont assembler deux element ensemble
                        if (!(results.size() <= 1)) {
                            convertBlockdata.put(results.get(0), results.get(1));
                        }
                        // Dans le cas ou la liste est impair , on peek un block aléatoire
                        else {
                            convertBlockdata.put(results.get(0), blockData.get(new Random().nextInt(blockData.size())));
                        }
                    });
                    convertBlockdata.entrySet().forEach(entry -> System.out.print("(" + entry.getKey() + "," + entry.getValue() + ") "));
                });
    }
}
