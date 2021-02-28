package eu.builderscoffee.expresso.utils.tuple;

import lombok.Data;

@Data(staticConstructor = "of")
public
class Triplet<U, V, T>
{
    public final U first;       // premier champ d'un Triplet
    public final V second;      // second champ d'un Triplet
    public final T third;       // troisième champ d'un Triplet
}