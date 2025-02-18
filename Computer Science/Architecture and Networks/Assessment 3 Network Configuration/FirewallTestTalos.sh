#!/bin/bash
# Change Read/Write/Execute Permission to Run: chmod +x firewall_test_internal.sh
# To Execute: ./firewall_test_internal.sh

echo ""
echo "Endpoint IP Address: $(hostname -I | awk '{print $1}')"
echo "Executing Talos Internal to Firewall Test Script..."
echo ""

check_connection() {
    local EXPECTED_BLOCKED=$3 
    echo -n "$1: "
    eval "timeout 5 $2" &> /dev/null
    if [ $? -eq 0 ]; then
        if [ "$EXPECTED_BLOCKED" == "yes" ]; then
            echo "❌ Test Failed"
        else
            echo "✅ Success"
        fi
    else
        if [ "$EXPECTED_BLOCKED" == "yes" ]; then
            echo "✅ Successfully Blocked"
        else
            echo "❌ Test Failed"
        fi
    fi
}

# Internal → DMZ Services (Allowed by Firewall)
echo "Testing Internal → DMZ Servers" 
check_connection "Internal → DMZ Web Server (HTTP) using Lynx" "lynx -dump www.talos.edu"
check_connection "Internal → DMZ Mail Server (SMTP) using Netcat" "nc -zv mail.talos.edu 25"
check_connection "Internal → DMZ DNS Server (ICMP)" "ping -c 5 81.17.69.10"
check_connection "Internal → DMZ Web Server (SSH - Port Check)" "nc -zv 81.17.69.11 22"
check_connection "Internal → DMZ Mail Server (SSH - Port Check)" "nc -zv 81.17.69.12 22"
echo ""

# Internal → External Services (Allowed by Firewall)
echo "Testing Internal → External Services" 
check_connection "Internal → Delos Web Server (HTTP) using Lynx" "lynx -dump www.delos.edu"
check_connection "Internal → Delos Mail Server (SMTP) using Netcat" "nc -zv mail.delos.edu 25"
check_connection "Internal → Delos Web Server (ICMP)" "ping -c 5 www.delos.edu" "yes"
check_connection "Internal → Delos Maileb Server (ICMP)" "ping -c 5 mail.delos.edu" "yes"
check_connection "Internal → Delos Web Server (SSH - Port Check)" "nc -zv www.delos.edu" "yes"
check_connection "Internal → Delos Mail Server (SSH - Port Check)" "nc -zv mail.delos.edu" "yes"
echo ""

# Internal → Talos Internal Servers (Blocked by Firewall)
echo "Testing Internal → Talos Internal Servers" 
check_connection "Internal → Talos Intranet Server (HTTP)" "lynx -dump 81.17.63.10" 
check_connection "Internal → Talos Local Web Server (HTTP)" "lynx -dump 81.17.181.10" 
# SSH Access (Allowed or Blocked by Firewall)
check_connection "Internal → Talos SSH Server (SSH - Port Check)" "nc -zv 81.17.97.10 22"
check_connection "Internal → Talos Local Web Server (SSH - Port Check)" "nc -zv 81.17.181.10 22"
check_connection "Internal → Talos Router R3 (SSH - Port Check)" "nc -zv 81.17.182.2 22"
# Ping Tests (Blocked by Firewall)
check_connection "Internal → Talos Intranet Web Server (ICMP)" "ping -c 5 81.17.63.10" 
check_connection "Internal → Talos Local Web Server (ICMP)" "ping -c 5 81.17.181.10" 
check_connection "Internal → Talos Router R3 Server (ICMP)" "ping -c 5 81.17.69.1"

echo ""
echo "✅ Firewall Test Completed."
echo ""
