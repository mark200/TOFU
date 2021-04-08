package nl.tudelft.oopp.group54.models.responseentities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GetPollStatsResponseTest {

	GetPollStatsResponse response;
	GetPollStatsResponse response2;
	GetPollStatsResponse response3;


	@BeforeEach
	void setUp() {
		Boolean success = true;
		String message = "Message";
		String correctAnswer = "D";
		Map<String, Integer> statsMap = new HashMap<>();
		statsMap.put("A", 25);
		statsMap.put("B", 25);
		statsMap.put("C", 50);
		Integer voteCount = 12;
		Integer optionCount = 3;

		response = new GetPollStatsResponse();
		response.setSuccess(success);
		response.setMessage(message);
		response.setCorrectAnswer(correctAnswer);
		response.setStatsMap(statsMap);
		response.setVoteCount(voteCount);
		response.setOptionCount(optionCount);

		response2 = new GetPollStatsResponse(
				success,
				message,
				correctAnswer,
				statsMap,
				voteCount,
				optionCount
		);

		response3 = new GetPollStatsResponse();
	}


	@Test
	void getSuccess() {
		assertEquals(response.getSuccess(), response2.getSuccess());
	}

	@Test
	void getMessage() {
		assertEquals(response.getMessage(), response2.getMessage());
	}

	@Test
	void getCorrectAnswer() {
		assertEquals(response.getCorrectAnswer(), response2.getCorrectAnswer());
	}

	@Test
	void getStatsMap() {
		assertEquals(response.getStatsMap(), response2.getStatsMap());
	}

	@Test
	void getVoteCount() {
		assertEquals(response.getVoteCount(), response2.getVoteCount());
	}

	@Test
	void getOptionCount() {
		assertEquals(response.getOptionCount(), response2.getOptionCount());
	}
}