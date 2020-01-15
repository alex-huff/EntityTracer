package phonis.entitytracer.trace;

import phonis.entitytracer.util.Offset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * OffsetType enum
 */
public enum OffsetType {
    BLOCKBOX;

    private final List<Offset> BLOCKBOXList = new ArrayList<>(
        Arrays.asList(
            new Offset(.49F, .49F, .49F),
            new Offset(-.49F, .49F, .49F),
            new Offset(-.49F, -.49F, .49F),
            new Offset(.49F, -.49F, .49F),
            new Offset(.49F, .49F, -.49F),
            new Offset(-.49F, .49F, -.49F),
            new Offset(-.49F, -.49F, -.49F),
            new Offset(.49F, -.49F, -.49F)
        )
    );

    /**
     * Gets offset of OffsetType
     *
     * @return List<Offset>
     */
    public List<Offset> getOffset() {
        return BLOCKBOXList;
    }
}
