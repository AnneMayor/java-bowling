package bowling.domain.frames;

import java.util.stream.Stream;

public interface FramesViewDto {
    public static final int MAX_FRAME_SIZE = 10;

    Stream<FrameViewDto> viewDtoStream();
}
