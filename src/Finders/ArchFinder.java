package Finders;

import Assemblies.ArchAssembly;
import Assemblies.VectorObjectAssembly;
import Objects.Shape;
import java.util.List;

public class ArchFinder {
  public void construct(Shape item) {
    int state = 0;
    ArchAssembly arch = new ArchAssembly();
    List<VectorObjectAssembly> items = item.vectorMap;

    for (int i = 0; i < item.vectorMap.size(); i++) {
      if (items.get(i) instanceof ArchAssembly) {
        switch (state) {
          case 0:
            arch.makeEqual((ArchAssembly)items.get(i));
            state++;
            break;

          case 1:
            if(arch.origin.distance(((ArchAssembly) items.get(i)).origin) > 1) {
              state = 0;
              break;
            }
            else {
              if (arch.end.distance(items.get(i).start) > 1) {
                arch.origin.average(((ArchAssembly) items.get(i)).origin);
                ArchAssembly newArch = new ArchAssembly(arch.start, items.get(i).end, arch.origin);
                item.vectorMap.remove(arch);
                item.vectorMap.remove(items.get(i));
                item.vectorMap.add((ArchAssembly)newArch);
              }
              state = 0;
              break;
            }
        }
      }
    }

  }
}
