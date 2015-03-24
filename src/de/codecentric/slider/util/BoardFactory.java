package de.codecentric.slider.util;

public class BoardFactory {
    private static final long[] BOARDS = { 
	    0x0fedcba987654321L, // 0
	    0xf0edcba987654321L, // 1
	    0xfe0dcba987654321L, // 2
	    0xfeadcb0987654321L, // 3
	    0xfeadcb9087654321L, // 4
	    0xfeadcb9587604321L, // 5
	    0xfeadcb9587064321L, // 6
	    0xfeadcb9580764321L, // 7
	    0xfeadcb9508764321L, // 8
	    0xfead0b95c8764321L, // 9
	    0x0eadfb95c8764321L, // 10
	    0xe0adfb95c8764321L, // 11
	    0xea0dfb95c8764321L, // 12
	    0xead0fb95c8764321L, // 13
	    0xead5fb90c8764321L, // 14
	    0xead5fb96c8704321L, // 15
	    0xead5fb96c8074321L, // 16
	    0xead5fb96c0874321L, // 17
	    0xead5f096cb874321L, // 18
	    0xead5f906cb874321L, // 19
	    0xea05f9d6cb874321L, // 20
	    0xea50f9d6cb874321L, // 21
	    0xea56f9d0cb874321L, // 22
	    0xea56f9d7cb804321L, // 23
	    0xea56f9d7cb084321L, // 24
	    0xea56f9d7cb284301L, // 25
	    0xea56f9d7cb284310L, // 26
	    0xea56f9d7cb204318L, // 27
	    0xea56f9d7cb024318L, // 28
	    0xea56f9d7cb124308L, // 29
	    0xea56f9d7cb124038L, // 30
	    0xea56f9d7cb120438L, // 31
	    0xea56f9d70b12c438L, // 32
	    0xea5609d7fb12c438L, // 33
	    0xea5690d7fb12c438L, // 34
	    0xea569d07fb12c438L, // 35
	    0xea569d17fb02c438L, // 36
	    0xea569d17fb20c438L, // 37
	    0xea569d17fb28c430L, // 38
	    0xea569d17fb28c403L, // 39
	    0xea569d17fb28c043L, // 40
	    0xea569d17f028cb43L, // 41
	    0xea569d170f28cb43L, // 42
	    0xea560d179f28cb43L, // 43
	    0xea56d0179f28cb43L, // 44
	    0xe056da179f28cb43L, // 45
	    0xe506da179f28cb43L, // 46
	    0xe516da079f28cb43L, // 47
	    0xe516da279f08cb43L, // 48
	    0xe516da279f48cb03L, // 49
	    0xe516da279f48c0b3L, // 50
	    0xe516da279048cfb3L, // 51
	    0xe516d0279a48cfb3L, // 52
	    0xe516d2079a48cfb3L, // 53
	    0xe516d2709a48cfb3L, // 54
	    0xe516d2789a40cfb3L, // 55
	    0xe516d2789a04cfb3L, // 56
	    0xe516d2789ab4cf03L, // 57
	    0xe516d2789ab4c0f3L, // 58
	    0xe516d2789ab40cf3L, // 59
	    0xe516d2780ab49cf3L, // 60
	    0xe5160278dab49cf3L, // 61
	    0x0516e278dab49cf3L, // 62
	    0x5016e278dab49cf3L, // 63
	    0x5106e278dab49cf3L, // 64
	    0x5160e278dab49cf3L, // 65
	    0x5168e270dab49cf3L, // 66
	    0x5168e274dab09cf3L, // 67
	    0x5168e274dab39cf0L, // 68
	    0x5168e274dab39c0fL, // 69
	    0x5168e274dab390cfL, // 70
	    0x5168e274dab309cfL, // 71
	    0x5168e2740ab3d9cfL, // 72
	    0x51680274eab3d9cfL, // 73
	    0x01685274eab3d9cfL, // 74
	    0x10685274eab3d9cfL, // 75
	    0x16085274eab3d9cfL, // 76
	    0x16805274eab3d9cfL, // 77
	    0x16845270eab3d9cfL, // 78
	    0x16845273eab0d9cfL, // 79
	    0x16845273eabfd9c0L, // 80
    };

    private BoardFactory() {
	// only static methods
    }
    
    public static SliderBoard createSliderBoard(int distanceToSolution) {
	return new SliderBoard(BOARDS[distanceToSolution]);
    }
    
    public static long createBoardAsLong(int distanceToSolution) {
	return BOARDS[distanceToSolution];
    }
    
    public static int maxAvailableDistanceToSolution() {
	return BOARDS.length - 1;
    }
}
