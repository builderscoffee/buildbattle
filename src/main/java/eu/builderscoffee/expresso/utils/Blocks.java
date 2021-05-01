package eu.builderscoffee.expresso.utils;

enum BlockData {

    STONE(1, 0, BlockCategory.CONSTRUCT),
    DIRT(1, 0, BlockCategory.CONSTRUCT);

    BlockData(int id, int Short, BlockCategory blockCategory) {

    }


    public enum BlockCategory {
        CONSTRUCT,
        CHEST,
        DOOR,
        STAIRS,
        SLAB
    }
}
