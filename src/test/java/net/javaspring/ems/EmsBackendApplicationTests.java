package net.javaspring.ems;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class EmsBackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testServiceTraffic(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity("https://school-blog-localzhe-storage.onrender.com/", String.class);
		System.out.println("Status:" + entity.getStatusCode());
		System.out.println("Body:" + entity.getBody());



		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch downLatch = new CountDownLatch(32);

		for(int count=0; count < 32; count++){
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try{
						int time = 0;
						while (time <= 100){
							try{
								RestTemplate restTemplate = new RestTemplate();
								restTemplate.getForEntity("https://school-blog-local-storage.onrender.com/", String.class);
								time++;
							}catch (Exception e){
								System.out.println("Request Failed:" + e.getMessage());
							}
						}
					}catch (Exception e){
						System.out.println("Thread error:" + e.getMessage());
						Thread.currentThread().interrupt();
					}finally {
						downLatch.countDown();
					}

				  }
				});
			}
		startLatch.countDown();
		try{
			downLatch.await();
		}catch (InterruptedException e){
			Thread.currentThread().interrupt();
		}

		executorService.shutdown();


	}

}
