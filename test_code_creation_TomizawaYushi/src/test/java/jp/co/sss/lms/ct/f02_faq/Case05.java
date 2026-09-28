package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 * @author 冨澤 雄志
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// トップページのURLにアクセスする
		goTo("http://localhost:8080/lms");

		// タイトルの判定処理
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// (初回ログイン済み受講生ユーザーの)ID, パスワードを入力
		webDriver.findElement(By.id("loginId")).clear();
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("password")).sendKeys("StudentAA00");

		// ログインボタンをクリック
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// コース詳細画面が表示されるまで待つ
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("li.active"), "コース詳細"));

		// タイトルの判定処理
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」ドロップダウンをクリック
		webDriver.findElement(By.linkText("機能")).click();

		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));

		// 「ヘルプ」リンクがクリックできるようになるまで待ち、クリック
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("ヘルプ")))
				.click();

		// ヘルプ画面が表示されるまで待つ
		wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));

		// タイトルの判定処理
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		// ウィンドウのハンドルを取得、最新のもの([1])へ移動
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);

		// タイトルの判定処理
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// 表示件数を全件に変更
		Select select = new Select(webDriver.findElement(By.cssSelector(".form-control.input-sm")));
		select.selectByVisibleText("ALL");

		// 質問と本文を取得(検索前)
		List<WebElement> allQuestions = webDriver.findElements(By.cssSelector("[id^='question-h']"));
		List<String> allQuestionTexts = new ArrayList<>();

		for (WebElement question : allQuestions) {
			allQuestionTexts.add(question.findElement(By.tagName("dt")).getText());
		}

		// 検索キーワード("？")を入力
		webDriver.findElement(By.id("form")).clear();
		webDriver.findElement(By.id("form")).sendKeys("？");

		// 「検索」ボタンをクリック
		webDriver.findElement(By.cssSelector("input[type='submit'][value='検索']")).click();

		// 質問と本文を取得(検索後)
		List<WebElement> searchedQuestions = webDriver.findElements(By.cssSelector("[id^='question-h']"));
		List<String> searchedQuestionTexts = new ArrayList<>();

		for (WebElement question : searchedQuestions) {
			searchedQuestionTexts.add(question.findElement(By.tagName("dt")).getText());
		}

		// 「？」を含む質問がすべて検索結果に存在するかの判定処理
		for (String question : allQuestionTexts) {
			if (question.contains("？")) {
				assertTrue(searchedQuestionTexts.contains(question));
			}
		}

		// 「？」を含まない質問が検索結果に存在しないかの判定処理
		for (String question : searchedQuestionTexts) {
			assertTrue(question.contains("？"));
		}

		// 検索結果へ移動
		scrollTo(String.valueOf(webDriver.findElement(By.className("sorting_asc")).getLocation().getY()));

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// 「クリア」ボタンをクリック
		webDriver.findElement(By.cssSelector("input[type='button'][value='クリア']")).click();

		// 入力値の判定処理
		assertEquals("", webDriver.findElement(By.id("form")).getText());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

}
