package compute.tpu;

//[START tpu_vm_start]

import com.google.cloud.tpu.v2.Node;
import com.google.cloud.tpu.v2.NodeName;
import com.google.cloud.tpu.v2.StartNodeRequest;
import com.google.cloud.tpu.v2.TpuClient;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class StartTpuVm {

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "tyaho-softserve-project";//"YOUR_PROJECT_ID";
    String zone = "europe-west4-a";
    String tpuVmName = "test-tpu-name";

    startTpuVm(projectId, zone, tpuVmName);
  }

  // Starts a TPU VM with the specified name in the given project and zone.
  public static void startTpuVm(String projectId, String zone, String tpuVmName)
      throws IOException, ExecutionException, InterruptedException {
    try (TpuClient tpuClient = TpuClient.create()) {
      String nodeName =
          NodeName.of(projectId, zone, tpuVmName).toString();

      StartNodeRequest request =
          StartNodeRequest.newBuilder().setName(nodeName).build();

      Node response = tpuClient.startNodeAsync(request).get();
      System.out.printf("TPU VM started: %s\n", response.getName());
    }
  }
}
//[END tpu_vm_start]
